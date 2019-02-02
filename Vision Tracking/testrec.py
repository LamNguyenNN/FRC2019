#!/usr/bin/env python3
#----------------------------------------------------------------------------
# Copyright (c) 2018 FIRST. All Rights Reserved.
# Open Source Software - may be modified and shared by FRC teams. The code
# must be accompanied by the FIRST BSD license file in the root directory of
# the project.
#----------------------------------------------------------------------------



import json
import time
import sys
import numpy as np
import cv2
from networktables import NetworkTables
from cscore import CameraServer, VideoSource, UsbCamera, MjpegServer

ip = '10.30.6.2'

NetworkTables.initialize(server=ip)

sd = NetworkTables.getTable("SmartDashboard")

lower_green = np.array([0, 90, 90]) # 0, 90, 90
upper_green = np.array([86, 255, 255]) #

camExposure = 40
camWidth = 320
camHeight = 240
center_x = camWidth * .5
margin = 20

'''
cs1 = CameraServer.getInstance()
cs2 = CameraServer.getInstance()
cs1.enableLogging()
cs2.enableLogging()

#usb1 = cs1.startAutomaticCapture(dev=0)
#usb2 = cs1.startAutomaticCapture(dev=1)

usb1.setResolution(camWidth, camHeight)
#usb2.setResolution(camWidth, camHeight)

usb1.setExposureManual(camExposure)
#usb2.setExposureManual(camExposure)

cvSink1 = cs1.getVideo(camera = usb1)
#cvSink2 = cs1.getVideo(camera = usb2)

outputStream1 = cs1.putVideo("Cam1", camWidth, camHeight)
#outputStream2 = cs1.putVideo("Cam2", camWidth, camHeight)

frame1 = np.zeros(shape=(camHeight, camWidth, 3), dtype=np.uint8)
#frame2 = np.zeros(shape=(camHeight, camWidth, 3), dtype=np.uint8)
'''

cs = CameraServer.getInstance()
cs.enableLogging()

cam1 = UsbCamera("cam1", 0)
cam2 = UsbCamera("cam2", 1)

cam1.setResolution(camWidth, camHeight)
cam2.setResolution(camWidth, camHeight)

cvSink = cs.getVideo(camera=cam1)
outputStream = cs.putVideo("Cam1", camWidth, camHeight)

frame1 = np.zeros(shape=(camHeight, camWidth, 3), dtype=np.uint8)

sd.putString('dir', 'working')

while(True):

    if(distance < 20):
        cvSink.setSource(cam2)
    else:
        cvSink.setSource(cam1)

    print("running")
    time1, frame1 = cvSink.grabFrame(frame1)
    #time2, frame2 = cvSink2.grabFrame(frame2)
    cv2.rectangle(frame1, (100, 100), (300, 300), (255, 255, 255), thickness=20)
    #cv2.rectangle(frame2, (100, 100), (200, 200), (0, 0, 0), thickness=20)
    outputStream.putFrame(frame1)
    #outputStream2.putFrame(frame2)	

NetworkTables.shutdown()
cap.release()
cv2.destroyAllWindows()

