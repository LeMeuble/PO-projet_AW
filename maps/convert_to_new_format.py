import os

METADATA_NAME_CHANGING = {
	
	"nbJoueurs": "players",
	"hauteur": "height",
	"largeur": "width",

}

def parse_metadata(string):

	metadata = {}
	parsed = string.split(',')
	
	metadata["name"] = "Unnamed"
	metadata["description"] = "No description"
	
	for elem in parsed:

		data = elem.split('=')

		if METADATA_NAME_CHANGING.get(data[0]) != None:

			newName = METADATA_NAME_CHANGING[data[0]]

			metadata[newName] = data[1]


	return metadata


for file in os.listdir():

	if file.endswith(".awdcmap"):

		name = file[:-8]

		if not os.path.exists(name + ".meta"): # Avoid already converted map

			with open(file, 'r') as f:

				metadata = parse_metadata(f.readline().strip())
				
			with open(name + '.meta', 'w+') as f:

				lines = []

				for key in metadata:

					lines.append(key + "=" + metadata[key])

				f.write('\n'.join(lines))
