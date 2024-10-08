import os

from PIL import Image

TO_UPDATE = ["dreadnought", "submarine", "cruiser", "landingship", "helicopter"]



def add_frame(path):

	for file in os.listdir(path):

		abs_path = os.path.abspath(os.path.normpath(os.path.join(path, file)))

		if os.path.isdir(abs_path) and file.startswith("move"):

			isright = False

			for tu in TO_UPDATE:
				if tu in abs_path:
					isright = True
					break

			if isright:

				im = Image.open(os.path.join(abs_path, "0.png"))

				im.save(os.path.join(abs_path, "2.png"))

		elif os.path.isdir(abs_path):
			add_frame(abs_path)


add_frame(".")