import os
import sys

from PIL import Image

first_resize = ['idle']

def rec_resize(path):

	for file in os.listdir(path):

		abs_path = os.path.abspath(os.path.normpath(os.path.join(path, file)))

		if os.path.isfile(abs_path):

			if file.endswith(".png"):

				im = Image.open(abs_path)

				if im.size == (24, 24):

					im = im.resize((96, 96), resample=Image.Resampling.NEAREST)

					print("UPSCALED to 96  " + abs_path)

					im.save(abs_path)

		else:
			rec_resize(abs_path)


rec_resize(".")