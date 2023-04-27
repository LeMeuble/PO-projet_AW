import os


TILE_VALUES = {
    0: "P;0",    # Plains
    3: "P;1",    # Plains Shadow
    5: "P;2",    # Shadow + Peak
    9: "P;3",    # Down-Right Road
    10: "P;4",   # Down-Left-Right Road
    11: "P;5",   # Down-Left Road
    12: "P;6",   # Down-Right Road + Shadow
    13: "P;7",   # Down-Left-Right Road + Shadow
    14: "P;8",   # Down-Left Road + Shadow
    17: "P;9",   # Top-Half Right Tree
    20: "P;10",  # Top-Half Left Tree
    21: "P;11",  # Weird Horizontal Bridge (+ Shadow?)
    26: "P;12",  # Peak
    31: "P;13",  # Down-Right-Up Road
    32: "P;14",  # Cross Road
    33: "P;15",  # Down-Left-Up Road
    34: "P;16",  # Down-Right-Up Road + Shadow
    35: "P;17",  # Cross Road + Shadow
    36: "P;18",  # Down-Left-Up Road + Shadow
    39: "P;19",  # Bottom-Half Right Tree
    42: "P;20",  # Bottom-Half Left Tree
    51: "P;21",  # Broken Horizontal Pipe
    52: "P;22",  # Broken Vertical Pipe
    53: "P;23",  # Right-Up Road
    54: "P;24",  # Left-Right-Up Road
    55: "P;25",  # Left-Up Road
    56: "P;26",  # Right-Up Road + Shadow
    57: "P;27",  # Left-Right-Up Road + Shadow
    58: "P;28",  # Left-Up Road + Shadow
    75: "P;29",  # Horizontal Road
    76: "P;30",  # Vertical Road
    77: "P;31",  # Horizontal Bridge
    78: "P;32",  # Vertical Bridge
    79: "P;33",  # Horizontal Road + Shadow
    80: "P;34",  # Vertical Road + Shadow
    88: "P;35",  # Debris

    1: "F;0",    # 2 Alone trees
    2: "F;1",    # 2 Alone trees + Shadow
    18: "F;2",   # Multiple Trees 1
    19: "F;3",   # Multiple Trees 2
    22: "F;4",   # Multiple Trees 3
    23: "F;5",   # Multiple Trees 4
    24: "F;6",   # Multiple Trees 5
    25: "F;7",   # Multiple Trees 6
    40: "F;8",   # Multiple Trees 7
    41: "F;9",   # Multiple Trees 8
    43: "F;10",  # Multiple Trees 9
    44: "F;11",  # Multiple Trees 10
    45: "F;12",  # Multiple Trees 11
    46: "F;13",  # Multiple Trees 12
    47: "F;14",  # Multiple Trees 13
    66: "F;15",  # Multiple Trees 14
    67: "F;16",  # Multiple Trees 15
    68: "F;17",  # Multiple Trees 16
    69: "F;18",  # Multiple Trees 17

    4: "M;0",    # Small Mountain
    6: "M;1",    # Small Mountain + Peak
    27: "M;2",   # Big Mountain
    28: "M;3",   # Big Mountain + Peak

    132: "W;0",
    133: "W;1",
    134: "W;2",
    135: "W;3",
    136: "W;4",
    137: "W;5",
    138: "W;6",
    139: "W;7",
    154: "W;8",
    155: "W;9",
    156: "W;10",
    157: "W;11",
    158: "W;12",
    159: "W;13",
    160: "W;14",
    161: "W;15",
    176: "W;16",
    177: "W;17",
    178: "W;18",
    179: "W;19",
    180: "W;20",
    181: "W;21",
    182: "W;22",
    183: "W;23",
    198: "W;24",
    199: "W;25",
    200: "W;26",
    201: "W;27",
    202: "W;28",
    203: "W;29",
    204: "W;30",
    205: "W;31",
    89: "W;32",
    90: "W;33",
    91: "W;34",
    92: "W;35",
    93: "W;36",
    94: "W;37",
    95: "W;38",
    96: "W;39",
    97: "W;40",
    118: "W;41",
    119: "W;42",
    111: "W;43",
    112: "W;44",
    113: "W;45",
    114: "W;46",
    115: "W;47",
    116: "W;48",
    117: "W;49",

    100: "B;0",
    101: "B;1",
    102: "B;2",
    99: "B;3",
    121: "B;4",
    122: "B;5",
    123: "B;6",
    124: "B;7",
    59: "B;8",
    60: "B;9",
    61: "B;10",
    62: "B;11",
    63: "B;12",
    64: "B;13",
    65: "B;14",
    81: "B;15",
    82: "B;16",
    83: "B;17",
    84: "B;18",
    85: "B;19",
    86: "B;20",
    87: "B;21",
    103: "B;22",
    104: "B;23",
    105: "B;24",
    106: "B;25",
    107: "B;26",
    108: "B;27",
    109: "B;28",
    125: "B;29",
    126: "B;30",
    127: "B;31",
    128: "B;32",
    129: "B;33",
    130: "B;34",
    131: "B;35",

    7: "O;0",    # Horizontal Pipe
    8: "O;1",    # Vertical Pipe
    29: "O;2",   # Horizontal Metal Pipe
    30: "O;3",   # Vertical Metal Pipe
    48: "O;4",   # Down-Right Pipe
    49: "O;5",   # Down-Left Pipe
    50: "O;6",   # Down Pipe
    70: "O;7",   # Right-Up Pipe
    71: "O;8",   # Left-Up Pipe
    72: "O;9",   # Up Pipe
    73: "O;10",  # Right Pipe
    74: "O;11",  # Left Pipe
    110: "O;12", # Water Rocks

    163: "h;0;1", # Red HQ
    164: "c;0;1", # Red City
    165: "f;0;1", # Red Factory
    166: "a;0;1", # Red Airport
    167: "p;0;1", # Red Port
    168: "h;0;2", # Blue HQ
    169: "c;0;2", # Blue City
    170: "f;0;2", # Blue Factory
    171: "a;0;2", # Blue Airport
    172: "p;0;2", # Blue Port
    185: "h;0;3", # Yellow HQ
    186: "c;0;3", # Yellow City
    187: "f;0;3", # Yellow Factory
    188: "a;0;3", # Yellow Airport
    189: "p;0;3", # Yellow Port
    190: "h;0;4", # Green HQ
    191: "c;0;4", # Green City
    192: "f;0;4", # Green Factory
    193: "a;0;4", # Green Airport
    194: "p;0;4", # Green Port
    207: "h;0;5", # Black HQ
    208: "c;0;5", # Black City
    209: "f;0;5", # Black Factory
    210: "a;0;5", # Black Airport
    211: "p;0;5", # Black Port
    213: "c;0;0", # White City
    214: "f;0;0", # White Factory
    215: "a;0;0", # White Airport
    216: "p;0;0", # White Port
}

def convert(value):

    return TILE_VALUES.get(int(value))

for file in os.listdir():

    if file.endswith(".csv"):

        result = []

        with open(file, 'r') as f:

            lines = f.read().split('\n')

            for i in range(len(lines)):

                line = lines[i].strip()

                if len(line) > 0:

                    result.append([])

                    columns = line.split(',')

                    for j in range(len(columns)):

                        result[i].append(convert(columns[j]))

        with open(file[:-3] + 'awdcmap', 'w+') as f:

            lines = []

            for i in range(len(result)):

                unit = []
                terrain = []

                for j in range(len(result[i])):

                    unit.append(" [.;.] ")

                    if result[i][j].startswith("c") or result[i][j].startswith("h") or result[i][j].startswith("f") or result[i][j].startswith("a") or result[i][j].startswith("p"):
                        terrain.append("{" + result[i][j] + "}")
                    else:
                        terrain.append("{" + result[i][j] + ";.}")

                lines.append(','.join(unit) + '\n' + ','.join(terrain))

            f.write('\n'.join(lines))