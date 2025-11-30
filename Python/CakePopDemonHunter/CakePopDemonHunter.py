import cv2
import numpy as np
import random
import time

class DemonDefenseGame:
    def __init__(self):
        # Initialize webcam
        self.cap = cv2.VideoCapture(0)
        
        # Get frame dimensions
        ret, frame = self.cap.read()
        if not ret:
            raise Exception("Could not access webcam")
        
        self.height, self.width = frame.shape[:2]
        
        # Initialize face detector
        self.face_cascade = cv2.CascadeClassifier(
            cv2.data.haarcascades + 'haarcascade_frontalface_default.xml'
        )
        
        # Game variables
        self.score = 0
        self.game_over = False
        self.face_rect = None
        
        # Corner demons
        self.corner_demons = []
        self.last_corner_spawn = time.time()
        self.corner_spawn_interval = 3.0  # seconds
        self.demon_size = 60
        
        # Falling demon
        self.falling_demon = None
        self.last_falling_spawn = time.time()
        self.falling_spawn_interval = 5.0  # seconds
        self.falling_speed = 3
        
    def spawn_corner_demon(self):
        """Spawn a demon in one of the four corners"""
        corners = [
            (30, 30),  # Top-left
            (self.width - self.demon_size - 30, 30),  # Top-right
            (30, self.height - self.demon_size - 30),  # Bottom-left
            (self.width - self.demon_size - 30, self.height - self.demon_size - 30)  # Bottom-right
        ]
        
        x, y = random.choice(corners)
        self.corner_demons.append({
            'x': x,
            'y': y,
            'active': True,
            'spawn_time': time.time()
        })
    
    def spawn_falling_demon(self):
        """Spawn a demon that falls from the top center"""
        if self.falling_demon is None:
            self.falling_demon = {
                'x': self.width // 2 - 40,
                'y': 0,
                'width': 80,
                'height': 100,
                'active': True
            }
    
    def check_motion_collision(self, frame_gray, prev_frame_gray):
        """Detect motion near corner demons"""
        if prev_frame_gray is None:
            return
        
        # Calculate frame difference
        frame_diff = cv2.absdiff(frame_gray, prev_frame_gray)
        _, thresh = cv2.threshold(frame_diff, 30, 255, cv2.THRESH_BINARY)
        
        # Check each corner demon for motion
        for demon in self.corner_demons:
            if demon['active']:
                x, y = demon['x'], demon['y']
                roi = thresh[y:y+self.demon_size, x:x+self.demon_size]
                
                if roi.size > 0:
                    motion_pixels = cv2.countNonZero(roi)
                    # If significant motion detected
                    if motion_pixels > 500:
                        demon['active'] = False
                        self.score += 1
    
    def check_face_demon_collision(self):
        """Check if falling demon intersects with face"""
        if self.falling_demon is None or self.face_rect is None:
            return False
        
        fx, fy, fw, fh = self.face_rect
        dx = self.falling_demon['x']
        dy = self.falling_demon['y']
        dw = self.falling_demon['width']
        dh = self.falling_demon['height']
        
        # Check rectangle intersection
        if (fx < dx + dw and fx + fw > dx and
            fy < dy + dh and fy + fh > dy):
            return True
        return False
    
    def update_falling_demon(self):
        """Update falling demon position"""
        if self.falling_demon and self.falling_demon['active']:
            self.falling_demon['y'] += self.falling_speed
            
            # Check if demon reached bottom
            if self.falling_demon['y'] > self.height:
                self.score += 5
                self.falling_demon = None
                self.last_falling_spawn = time.time()
    
    def draw_game_elements(self, frame):
        """Draw all game elements on the frame"""
        # Draw corner demons (circles)
        for demon in self.corner_demons:
            if demon['active']:
                center_x = demon['x'] + self.demon_size // 2
                center_y = demon['y'] + self.demon_size // 2
                cv2.circle(frame, (center_x, center_y), self.demon_size // 2, (0, 0, 255), -1)
                cv2.circle(frame, (center_x, center_y), self.demon_size // 2, (0, 0, 0), 2)
        
        # Remove inactive corner demons after a delay
        self.corner_demons = [d for d in self.corner_demons if d['active'] or 
                             time.time() - d.get('spawn_time', 0) < 0.5]
        
        # Draw falling demon (rectangle)
        if self.falling_demon and self.falling_demon['active']:
            x = self.falling_demon['x']
            y = self.falling_demon['y']
            w = self.falling_demon['width']
            h = self.falling_demon['height']
            cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 0, 255), -1)
            cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 0, 0), 2)
        
        # Draw face rectangle
        if self.face_rect is not None:
            x, y, w, h = self.face_rect
            cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 3)
        
        # Draw score
        cv2.putText(frame, f'Score: {self.score}', (10, 40), 
                   cv2.FONT_HERSHEY_SIMPLEX, 1.5, (255, 255, 255), 3)
        
        # Draw game over message
        if self.game_over:
            text = "GAME OVER"
            font = cv2.FONT_HERSHEY_SIMPLEX
            text_size = cv2.getTextSize(text, font, 3, 5)[0]
            text_x = (self.width - text_size[0]) // 2
            text_y = (self.height + text_size[1]) // 2
            
            # Draw background rectangle
            cv2.rectangle(frame, (text_x - 20, text_y - text_size[1] - 20),
                         (text_x + text_size[0] + 20, text_y + 20), (0, 0, 0), -1)
            cv2.putText(frame, text, (text_x, text_y), font, 3, (0, 0, 255), 5)
            
            # Draw final score
            final_text = f"Final Score: {self.score}"
            final_size = cv2.getTextSize(final_text, font, 1.5, 3)[0]
            final_x = (self.width - final_size[0]) // 2
            cv2.putText(frame, final_text, (final_x, text_y + 80), 
                       font, 1.5, (255, 255, 255), 3)
    
    def run(self):
        """Main game loop"""
        prev_frame_gray = None
        
        print("Demon Defense Game")
        print("------------------")
        print("Instructions:")
        print("- Move to destroy corner demons (red circles)")
        print("- Avoid the falling demon (red rectangle) from the top")
        print("- Press 'q' to quit")
        print()
        
        while True:
            ret, frame = self.cap.read()
            if not ret:
                break
            
            # Mirror the frame
            frame = cv2.flip(frame, 1)
            
            # Convert to grayscale for face detection
            gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
            
            if not self.game_over:
                # Detect faces
                faces = self.face_cascade.detectMultiScale(gray, 1.3, 5)
                
                if len(faces) > 0:
                    # Use the largest face detected
                    self.face_rect = max(faces, key=lambda rect: rect[2] * rect[3])
                
                # Spawn corner demons periodically
                if time.time() - self.last_corner_spawn > self.corner_spawn_interval:
                    self.spawn_corner_demon()
                    self.last_corner_spawn = time.time()
                
                # Spawn falling demon periodically
                if time.time() - self.last_falling_spawn > self.falling_spawn_interval:
                    self.spawn_falling_demon()
                
                # Check motion collision with corner demons
                if prev_frame_gray is not None:
                    self.check_motion_collision(gray, prev_frame_gray)
                
                # Update falling demon
                self.update_falling_demon()
                
                # Check collision between face and falling demon
                if self.check_face_demon_collision():
                    self.game_over = True
            
            # Draw all game elements
            self.draw_game_elements(frame)
            
            # Display the frame
            cv2.imshow('Demon Defense Game', frame)
            
            # Store current frame for next iteration
            prev_frame_gray = gray.copy()
            
            # Check for quit key
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break
        
        self.cap.release()
        cv2.destroyAllWindows()

game = DemonDefenseGame()
game.run()