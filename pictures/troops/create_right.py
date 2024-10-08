import os

from PIL import Image


def rec_righizer(path):

	for file in os.listdir(path):

		abs_path = os.path.abspath(os.path.normpath(os.path.join(path, file)))

		if os.path.isdir(abs_path) and file == "unavailableleft":

			if "aircraft_carrier" in abs_path or "corvette" in abs_path:

				newFolder = os.path.join(path, "unavailableright")

				if not os.path.exists(newFolder):
					os.mkdir(newFolder)


				for frame in os.listdir(abs_path):

					if frame.endswith(".png"):

						im = Image.open(os.path.join(abs_path, frame))
						im = im.transpose(Image.Transpose.FLIP_LEFT_RIGHT)

						im.save(os.path.join(newFolder, frame))

						print("TRANPOSED " + frame + " from " + abs_path + " to " + newFolder)

		elif os.path.isdir(abs_path):
			rec_righizer(abs_path)


rec_righizer(".")