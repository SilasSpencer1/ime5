# image manipulation use Information

Arguments: please use the prompt argument.

type "-text" for modifying images via the command line

or 

type "-script" and then type your script filepath into the 
command line to modify images via a script.

ex) "-script" , "src/script.txt"

Commands:

--------------------------------------------------------------------------
- supports file types: PPM
--------------------------------------------------------------------------

q/Q - quit the program, cannot be used within a command where a command
requires additional input

-----------------------
Modify Layers Commands:
-----------------------

create (name) - adds an invisible layer/unit/canvas to the top of the layered images
keeping the current at the same index layer

Example: create first

setCurrent (int) - sets the layer currently working on to the layer at the
given index if there is a layer, else current stays the same
additional input must be an integer after command
EX: setCurrent 1
setCurrent 0 -> "Invalid index"
--------------------------------------------------------------------------

------------------------------
Import/Export Images Commands:
------------------------------
- program can import or load single ppm images at the current layer
- there must be a layer for loading or saving a ppm image.


load (String) - loads the image of the given file name onto the current layer
EX: load src/ocean.ppm
load ocean.jpg, load ocean.txt -> "Invalid file type"

save (String) - saves the topmost visible layer as an image if there
is an image at the layer
requires additional input for the file name to save as
EX: save Koala.txt -> "Invalid file name"
(if image doesn't exist) save Koala.jpeg -> "No image to save"
(no visible layers) save Koala.jpeg -> "No visible layers to save image"

--------------------------------------------------------------------------

------------------------
Editing Images Commands:
------------------------
- modifies at current layer
- must be some image on the layerto edit

grayscale-i - applies the grayscale transformation on the image at the
current layer via the intensity.

create first load src/ocean.ppm grayscale-i

grayscale-l - applies the grayscale transformation on the image at the
current layer via the luma.

create first load src/ocean.ppm grayscale-l

grayscale-r, grayscale-b, grayscale-g, -> pplies the grayscale transformation on the image at the
current layer via the specific color component.

ex) create first load src/ocean.ppm grayscale-g

ex) create first load src/ocean.ppm grayscale-r

brigthen (value) - brightens the image by a user implemented
value or darkens if this value is negaive.

ex) brighten 10

flip-v, flip-h -> flips the image on the current layer either horizontally (flip-h) or vertically (flip-v)

ex) create first load src/ocean.ppm flip-v

ex) create first load src/ocean.ppm flip-h 
