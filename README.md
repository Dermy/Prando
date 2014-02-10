Prando
======

Overview
--------

Prando stands for Patch, Rename, and Organize. It is a tool for batch manipulating STFS packages (downloadable content, title updates, etc.). It supports CON, LIVE, and PIRS files. It runs on Windows, Mac, and Linux.

**Java Runtime Environment 7 (1.7) is required for Prando to run.**

Features
--------

* Patch (Unlock) packages for use on JTAG and RGH consoles.
* Rename packages to their original filename. Useful for packages that have those 42 digit filenames that contain numbers 0-9 and letters A-F.
* Organize packages into their correct directories so you can easily transfer them to a JTAG or RGH console.
* View multiple packages in a customizable, sortable, searchable table. Click a table header to sort in ascending or descending order by that category.
* Select a package from the table to show more information including all 16 License Entries in hexadecimal.

Usage
-----

1. Run Prando.
2. Choose a folder to recursively look for STFS packages in or choose a single file for Prando to load.
3. View data for individual packages by selecting them in the table. Click and drag to the right on a text field in the bottom right corner of Prando if the data is larger than the size of a text field to view the rest of it.
4. Tick the checkboxes of operations you would like to perform on the packages (Patch, Rename, and/or Organize).
5. Press Go to perform the operations. If Organize has been selected, also choose a directory where you would like the newly organized files to be placed.

Todo
----

* Comment code.
* Make Open New File/Directory button look like it is clickable (because it is).
* Make file chooser headers stay extended and not shrink when you change to a different directory.
* Make a way to sort filesizes in table by KB, MB, GB instead of bytes.
* Improve search regex.
* Find out if there is a way to stop slowness when scrolling through the table using keyboard arrow keys. Refreshing all of the info fields quickly time will cause the program to lag after a short time.

Screenshots
-----------

![Screenshot-1](/screenshots/Screenshot-1.png?raw=true)
![Screenshot-2](/screenshots/Screenshot-2.png?raw=true)