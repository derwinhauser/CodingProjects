import cv2

# Initialize webcam
cap = cv2.VideoCapture(0)

while True:
    ret, frame = cap.read()
    if not ret:
        break
    
    # Mirror the frame
    frame = cv2.flip(frame, 1)
    
    # Display the frame
    cv2.imshow('Webcam', frame)
    
    # Check for quit key
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()