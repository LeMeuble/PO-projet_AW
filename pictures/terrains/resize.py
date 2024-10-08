import os

from PIL import Image


def rec_resize(path):

	for file in os.listdir(path):

		abs_path = os.path.abspath(os.path.normpath(os.path.join(path, file)))

		if os.path.isfile(abs_path):

			if file.endswith(".png"):

				im = Image.open(abs_path)

				if im.size == (16, 16):

					print("UPSCALING to 64  " + abs_path)

					im = im.resize((64, 64), resample=Image.Resampling.NEAREST)
					im.save(abs_path)

					print("OK!")

		else:
			rec_resize(abs_path)


rec_resize(".")