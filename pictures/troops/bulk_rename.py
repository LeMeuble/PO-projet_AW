import os

FROM = "corvet"
TO = "corvette"


def rename(path):

	for file in os.listdir(path):

		abs_path = os.path.abspath(os.path.normpath(os.path.join(path, file)))

		if file == FROM:

			os.rename(abs_path, os.path.abspath(os.path.normpath(os.path.join(path, TO))))
			print("RENAMED " + abs_path + " to " + os.path.abspath(os.path.normpath(os.path.join(path, TO))))

		elif os.path.isdir(abs_path):
			rename(abs_path)


rename(".")