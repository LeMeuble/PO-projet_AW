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

				if im.size == (96, 96):

					print("OUTLINING " + abs_path)

					im = im.crop((4, 4, 92, 92))

					print("UPSCALED to 96  " + abs_path)

					im.save(abs_path)

				elif im.size == (88, 88):

					print("OUTLINING " + abs_path)

					im = im.crop((4, 4, 84, 84))

					print("UPSCALED to 96  " + abs_path)

					im.save(abs_path)

		else:
			rec_resize(abs_path)


rec_resize(".")