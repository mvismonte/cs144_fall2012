UCLA CS 144
Fall 2012
Project 3
Mark Vismonte
Logan Chang
11/11/2012

Project Part 2
=====================
We had to fix a bug in our project part 2 where the whole description wasn't
being loaded into the DB.  This was a problem with the way that MYSQL loads
CSV files (with quoting).


Project Part 3A
=====================
We updated our indexes to make sure we only stored the fields that we wanted
and that we indexed only the fields we wanted.  This was reflected upon our
implementation of basicSearch and advancedSearch in Project Part 3B.

We created a field in the index called content, which contains the name,
description, and categories stored as a single string.  This will be used for
basicSearch in Part 3B.  We also created name, content, and categories
fields which are used for advancedSearch (so that we can look up strings in
specific fields).


Project Part 3B
=====================
We implemented basicSearch by doing a Lucene search over the content field.

We implemented advancedSearch by doing a Lucene search over the name,
description, or categories fields if those constraints are passed in.
Otherwise, we will do a search in MYSQL for the fields.

For each constraint, we will obtain a set of SearchResults.  Afterwards, we
can find the intersection of all these sets to obtain our final resulting
set.