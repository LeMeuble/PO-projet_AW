import os

from PIL import Image


def add_frame(path):

	for file in os.listdir(path):

		abs_path = os.path.abspath(os.path.normpath(os.path.join(path, file)))

		if os.path.isdir(abs_path) and file.startswith("move"):

			if len(os.listdir(abs_path)) == 2:

				im = Image.open(os.path.join(abs_path, "1.png"))

				im.save(os.path.join(abs_path, "2.png"))

		elif os.path.isdir(abs_path):
			add_frame(abs_path)


add_frame(".")