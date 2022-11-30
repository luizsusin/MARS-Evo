# MARS Evo

A [MARS](http://courses.missouristate.edu/KenVollmar/mars/) (MIPS Assembler and Runtime Simulator), tool similar to the MARS Bot, used to draw things on screen using a simple MMIO interface. It is possible to draw full RGB-colored images using MIPS Assembly. This can be used together with [MARS Scanner](https://github.com/luizsusin/MARS-Scanner) to print JPEG full-color images.

![MARS Evo screenshot](https://i.imgur.com/aYWNcQ5.png)

## Getting Started

### Prerequisites
To use MARS Evo, you must have:

```
* 7-Zip, WinRAR or any other jar and zip file viewer and editor;
* A MARS 4.5 (or newer) jar file; and
* The binaries of this repository.
```
All the binaries released can be found at the page [releases](https://github.com/luizsusin/MARS-Evo/releases).

### Updating from previous version

In case you're updating from an older version, follow these instructions:
1. Open the MARS 4.5 (or newer) jar file using your jar file viewer;
2. Access mars/tools and delete the folder marsevo;
3. Go back to the root folder of the jar file;
4. Open the binaries using any zip file viewer;
5. Copy the contents of the binaries zip file inside the MARS jar file.

### Installing

To install MARS Evo, you must:
1. Open the binaries using any zip file viewer;
2. Open the MARS 4.5 (or newer) jar file using your jar file viewer;
3. Copy the contents of the binaries zip file inside the MARS jar file.

And you're good to go!

## Using the tool

To use MARS Evo, you must understand how MMIO (Memory Mapped I/O) works. In case you don't, I suggest you have some [reading](https://en.wikipedia.org/wiki/Memory-mapped_I/O) before proceeding.
Once you understand how it works, take note of these informations:

### Addresses used by MARS Evo

MARS Evo uses the same addresses as the MARS Bot tool. However, it has a few more features, so it also has a few more addresses to communicate back and forth with MARS, which are:

```
* 0xFFFF8010 - Used to set the heading of the printhead;
* 0xFFFF8020 - Used to set the printhead tracer on (1) and off (0);
* 0xFFFF8030 - Used to get the current X position of the printhead;
* 0xFFFF8040 - Used to get the current Y position of the printhead;
* 0xFFFF8050 - Used to turn the movement of the printhead;
* 0xFFFF8060 - Used to set the speed of the printhead (25-1000 px/sec);
* 0xFFFF8070 - Used to set the R-channel value (0-255) of the trace from the printhead;
* 0xFFFF8080 - Used to set the G-channel value (0-255) of the trace from the printhead;
* 0xFFFF8090 - Used to set the B-channel value (0-255) of the trace from the printhead;
```

### Turning the printhead tracer on and off

To turn the printhead tracer on, you must set the 0xFFFF8020 address to 1. E.g.:

```
#MIPS Assembly

.data
.text
    li $s0,0xFFFF8020 # Sets the address 0xFFFF8020 into the $s0 registrator
    li $t0,1 # Sets the $t0 registrator as 1
    sw $t0,0($s0) # Sets the value in the $s0 address to the value in the $t0 registrator
```

To turn it off, just change the value in the address to 0. E.g:

```
#MIPS Assembly

.data
.text
    li $s0,0xFFFF8020 # Sets the address 0xFFFF8020 into the $s0 registrator
    sw $zero,0($s0) # Sets the value in the $s0 address to the value in the $zero registrator
```

### Changing the color on the printhead

To change the color that the printhead is printing, you must set the R, G and B color channels in the memory. E.g.:

```
#MIPS Assembly

.data
.text
    li $s0,0xFFFF8070 # Sets the address to the R-channel (0xFFFF8070) into the $s0 registrator
    li $t3,65 # Sets the value of the $t3 registrator as 65
    sw $t3,0($s0) # Sets the value in the $s0 (stored address) to the value of the $t3 registrator
            
    li $s0,0xFFFF8080 # Sets the address to the G-channel (0xFFFF8080) into the $s0 registrator
    li $t3,184 # Sets the value of the $t3 registrator as 184
    sw $t3,0($s0) # Sets the value in the $s0 (stored address) to the value of the $t3 registrator
    
    li $s0,0xFFFF8090 # Sets the address to the B-channel (0xFFFF8090) into the $s0 registrator
    li $t3,208 # Sets the value of the $t3 registrator as 208
    sw $t3,0($s0) # Sets the value in the $s0 (stored address) to the value of the $t3 registrator
```

The result will be shown as the following color in the MARS Evo interface:

![MARS Evo color](https://i.imgur.com/zvbq4QT.png)

## Authors

* **Luiz H. Susin** - *Initial work* - [GitHub](https://github.com/LuizSusin)

## License

This project is licensed under the GNU General Public License version 3 - see the [LICENSE.md](LICENSE.md) file for details.

## TO DO List

* Nothing for now! Be free to suggest me something new.
