import cv2
import numpy as np
import random
import time

# Initialize webcam
cap = cv2.VideoCapture(0)

# Get frame dimensions
ret, frame = cap.read()
height, width = frame.shape[:2]

# Initialize face detector
face_cascade = cv2.CascadeClassifier(
    cv2.data.haarcascades + 'haarcascade_frontalface_default.xml'
)

face_rect = None
score = 0
prev_frame_gray = None

# Corner demons
corner_demons = []
last_corner_spawn = time.time()
corner_spawn_interval = 3.0
demon_size = 60

def spawn_corner_demon():
    corners = [
        (30, 30),
        (width - demon_size - 30, 30),
        (30, height - demon_size - 30),
        (width - demon_size - 30, height - demon_size - 30)
    ]
    x, y = random.choice(corners)
    corner_demons.append({'x': x, 'y': y, 'active': True})

def check_motion_collision(frame_gray, prev_frame_gray):
    global score
    
    if prev_frame_gray is None:
        return
    
    # Calculate frame difference
    frame_diff = cv2.absdiff(frame_gray, prev_frame_gray)
    _, thresh = cv2.threshold(frame_diff, 30, 255, cv2.THRESH_BINARY)
    
    # Check each corner demon for motion
    for demon in corner_demons:
        if demon['active']:
            x, y = demon['x'], demon['y']
            roi = thresh[y:y+demon_size, x:x+demon_size]
            
            if roi.size > 0:
                motion_pixels = cv2.countNonZero(roi)
                if motion_pixels > 500:
                    demon['active'] = False
                    score += 1

while True:
    ret, frame = cap.read()
    if not ret:
        break
    
    # Mirror the frame
    frame = cv2.flip(frame, 1)
    
    # Convert to grayscale
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    # Detect faces
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    
    # Use the largest face detected
    if len(faces) > 0:
        face_rect = max(faces, key=lambda rect: rect[2] * rect[3])
    
    # Spawn corner demons periodically
    if time.time() - last_corner_spawn > corner_spawn_interval:
        spawn_corner_demon()
        last_corner_spawn = time.time()
    
    # Check motion collision with corner demons
    if prev_frame_gray is not None:
        check_motion_collision(gray, prev_frame_gray)
    
    # Draw corner demons
    for demon in corner_demons:
        if demon['active']:
            center_x = demon['x'] + demon_size // 2
            center_y = demon['y'] + demon_size // 2
            cv2.circle(frame, (center_x, center_y), demon_size // 2, (0, 0, 255), -1)
            cv2.circle(frame, (center_x, center_y), demon_size // 2, (0, 0, 0), 2)
    
    # Draw rectangle around face
    if face_rect is not None:
        x, y, w, h = face_rect
        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 3)
    
    # Draw score
    cv2.putText(frame, f'Score: {score}', (10, 40), 
               cv2.FONT_HERSHEY_SIMPLEX, 1.5, (255, 255, 255), 3)
    
    # Display the frame
    cv2.imshow('Demon Defense', frame)
    
    # Store current frame
    prev_frame_gray = gray.copy()
    
    # Check for quit key
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break


cap.release()
cv2.destroyAllWindows()