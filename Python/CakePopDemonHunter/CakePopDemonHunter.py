import cv2

# Initialize webcam
cap = cv2.VideoCapture(0)

# Initialize face detector
face_cascade = cv2.CascadeClassifier(
    cv2.data.haarcascades + 'haarcascade_frontalface_default.xml'
)

face_rect = None

while True:
    ret, frame = cap.read()
    if not ret:
        break
    
    # Mirror the frame
    frame = cv2.flip(frame, 1)
    
    # Convert to grayscale for face detection
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    
    # Detect faces
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    
    # Use the largest face detected
    if len(faces) > 0:
        face_rect = max(faces, key=lambda rect: rect[2] * rect[3])
    
    # Draw rectangle around face
    if face_rect is not None:
        x, y, w, h = face_rect
        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 3)
    
    # Display the frame
    cv2.imshow('Face Detection', frame)
    
    # Check for quit key
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()