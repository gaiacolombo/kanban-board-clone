# Kanban Board Clone

A web application that allows a development team to organize their work and track progress, similar to KanbanFlow, Trello, and Kanboard.

## Board Structure

The board is structured with columns and tiles.

### Tile

- A **tile** is the smallest unit on the board. Each tile has:
  - A title
  - An author
  - Content
  - A type of message (organizational or informative)

- Tiles can have textual or multimedia content:
  - *Textual tile*: Contains a paragraph of text with an undefined length.
  - *Multimedia tile*: Contains an image file. If the uploaded image is larger than 900x900, its resolution will be automatically reduced.

### Column

- Each **column** can contain zero or more tiles and is defined by:
  - A title
  - A current state (either *in progress* or *archived*)

## Web Interface Features

- Visualize columns and their contained tiles.
- Create new columns and new tiles (each tile must be assigned to a column).
- Modify tiles and move them to different columns.
- Modify a column's title (must remain unique).
- Change a column's state between *in progress* and *archived*.
- Columns in different states are displayed on different pages.
- Archived columns cannot be modified or deleted, nor can new tiles be added to them.
- *In progress* columns can be deleted, which also deletes all associated tiles.

For further instructions, refer to the `instructions.pdf` file, which includes specifics on how to test and use the project.

## Technologies

- Java with Spring Framework
- MySQL Database
- Bootstrap for web interfaces
