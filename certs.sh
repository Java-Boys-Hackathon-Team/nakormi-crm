#!/usr/bin/env bash

sudo apt update
sudo apt install certbot
sudo certbot certonly --standalone -d nakormi.kuramshin-dev.ru