import os
import sys

from PIL import Image


def rec_resize(path):

	for file in os.listdir(path):

		abs_path = os.path.abspath(os.path.normpath(os.path.join(path, file)))

		if os.path.isfile(abs_path):

			if file.endswith(".png"):

				im = Image.open(abs_path)

				if im.size == (16, 16):

					print("OUTLINING " + abs_path)

					newim = Image.new("RGBA", (24, 24), (0, 0, 0, 0))
					newim.paste(im, (4, 4))
					newim = newim.resize((96, 96), resample=Image.Resampling.NEAREST)

					print("UPSCALING to 96  " + abs_path)

					newim.save(abs_path)

				# elif im.size == (24, 24):

				# 	skip = ["lander", "bomber", "battleship", "cruiser", "helicopter", "fighter_plane", "transport_copter"]

				# 	if "move" in abs_path:

				# 		shift = True

				# 		for s in skip:

				# 			if s in abs_path:

				# 				shift = False
				# 				break

				# 		if shift:

				# 			print("SHIFTING " + abs_path)
							
				# 			newim = Image.new("RGBA", (24, 24), (0, 0, 0, 0))
				# 			newim.paste(im.crop((0, 4, 24, 24)), (0, 0))
				# 			im = newim

				# 		print("UPSCALING to 96  " + abs_path)

				# 		im = im.resize((96, 96), resample=Image.Resampling.NEAREST)
				# 		im.save(abs_path)


		else:
			rec_resize(abs_path)


rec_resize(".")