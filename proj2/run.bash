#!/bin/bash
set -u -e
javac Game.java View.java Controller.java Json.java
java Game
