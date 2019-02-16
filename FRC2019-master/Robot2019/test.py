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

camWidth = 320
camHeight = 240
center_x = camWidth * .5
margin = 20

cs1 = CameraServer.getInstance()
cs2 = CameraServer.getInstance()
cs1.enableLogging()
cs2.enableLogging()

usb1 = cs1.startAutomaticCapture(dev=0)
usb2 = cs2.startAutomaticCapture(dev=1)

usb1.setResolution(camWidth, camHeight)
usb2.setResolution(camWidth, camHeight)

usb1.setExposureManual(1)
usb2.setExposureManual(1)

cvSink1 = cs1.getVideo(camera = usb1)
cvSink2 = cs2.getVideo(camera = usb2)

outputStream1 = cs1.putVideo("Cam1", camWidth, camHeight)
outputStream2 = cs2.putVideo("Cam2", camWidth, camHeight)

frame1 = np.zeros(shape=(camHeight, camWidth, 3), dtype=np.uint8)
frame2 = np.zeros(shape=(camHeight, camWidth, 3), dtype=np.uint8)

sd.putString('dir', 'working')

while(True):

	time1, frame1 = cvSink1.grabFrame(frame1)
	time2, frame2 = cvSink2.grabFrame(frame2)
	
	hsv = cv2.cvtColor(frame1, cv2.COLOR_BGR2HSV)
	mask = cv2.inRange(hsv, lower_green, upper_green)
	contours = cv2.findContours(mask,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)[-2] 
	
	largest_area = 0
	largest_contour = np.array([0, 0])
	sec_largest_area = 0
	sec_largest_contour = np.array([0, 0])
	
	for c in contours:
		area = cv2.contourArea(c)
		if area > largest_area:
			sec_largest_contour = largest_contour
			sec_largest_area = largest_area
			largest_contour = c
			largest_area = area
		elif area > sec_largest_area:
			sec_largest_contour = c
			sec_largest_area = area
	
	print(largest_area)
	print(sec_largest_area)		
	
	if(largest_area > 0 and sec_largest_area > 0):
		M1 = cv2.moments(largest_contour)
		center_contour_x = int(M1['m10']/M1['m00'])
		
		M2 = cv2.moments(sec_largest_contour)
		center_contour_x_2 = int(M2['m10']/M2['m00'])
		
		center_average_x = .5 * (center_contour_x + center_contour_x_2)
		
		if abs(center_average_x - center_x) > margin:
			if center_average_x < center_x:
				sd.putString('dir', 'r')
				print('r')
			elif center_average_x > center_x:
				sd.putString('dir', 'l')
				print('l')
		else:
			sd.putString('dir', 'f')
			print('f')
		
		cv2.drawContours(frame1, [largest_contour], 0, (0, 255, 0), 3)
		cv2.drawContours(frame1, [sec_largest_contour], 0, (0, 255, 0), 3)
	outputStream1.putFrame(frame1)
	outputStream2.putFrame(frame2)
	
NetworkTables.shutdown()
cap.release()
cv2.destroyAllWindows()
