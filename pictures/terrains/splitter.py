import os
import sys

from PIL import Image

# ./splitter.py <file> <column_count> <row_count> <margin> <output>

if len(sys.argv) == 6:

	try:

		file = sys.argv[1]
		column_count = int(sys.argv[2])
		row_count = int(sys.argv[3])
		margin = int(sys.argv[4])
		output_dir = sys.argv[5]

		if not os.path.exists(output_dir):
			os.mkdir(output_dir)

		image = Image.open(file)
		image = image.convert(mode="RGB")

		width = image.size[0]
		height = image.size[1]

		tile_size = int((width - (margin * column_count) - margin) / column_count)

		image_n = 0

		for row in range(row_count):

			for col in range(column_count):

				output = Image.new("RGB", (tile_size, tile_size))

				x_min = margin + col * (tile_size + margin)
				x_max = margin + col * (tile_size + margin) + tile_size

				y_min = margin + row * (tile_size + margin)
				y_max = margin + row * (tile_size + margin) + tile_size

				for x in range(x_min, x_max):
					for y in range(y_min, y_max):
						output.putpixel((x - x_min, y - y_min), image.getpixel((x, y)))	

				output.save(os.path.join(output_dir, str(image_n) + ".png"))
				image_n += 1


		print('DONE!')
		sys.exit(0)

	except ValueError as e:

		print("Invalid integer!")



print("Usage : ./splitter.py <file> <column_count> <row_count> <margin> <output>")