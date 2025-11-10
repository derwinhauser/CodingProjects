print('This is mirror.py')

import numpy as np 
import cv2 as cv 

cap = cv.VideoCapture(0)

if not cap.isOpened():
    print("Cannot open camera")
    exit()

while True:
    #Capture frame-by-frame
    ret, frame = cap.read()

    # turn the image into mirror image
    frame = cv.flip(frame, 1)
    height, width = frame.shape[:2]

    # if frame is read correctly ret is True
    if not ret:
        print("Can't receive frame  (stream end?). Exiting")
        break

    # Our operations on the frame come here
    gray = cv.cvtColor(frame, cv.COLOR_BGR2GRAY)

    # Display Text
    cv.putText(frame,"Hello",(50,100),
    cv.FONT_HERSHEY_SIMPLEX,
    1, # font_scale
    (100,0,100), # color
    2)  # Thickness

    # Display the resulting frame

    # cv.imshow('frame', gray)
    cv.imshow('frame', frame)
    if cv.waitKey(1) == ord('q'):
        break

# When everything is done, release the capture
cap.release()
cv.destroyAllWindows()