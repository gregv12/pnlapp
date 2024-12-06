#!/usr/bin/env bash

# Get the absolute path of the script
script_dir="$(dirname "$(readlink -f "$0")")"

# Change the directory to the script's directory
cd "$script_dir"/..

echo running in `pwd`
docker build -t gregh101/pnl-app .
