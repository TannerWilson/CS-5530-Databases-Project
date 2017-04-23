1. Compile the java code in WEB-INF/classes: javac cs5530/*.java

2. Edit the web.xml in WEB-INF; replace Your Name with your first name
and last name.

3. Access your homepage via http://georgia.eng.utah.edu:8080/~5530ux
(replace x with your user id, e.g., 5530u01).

CS 5530 database 58 team project phase 3.

Authors:
    Gradey Cullins
    Tanner Wilson

Existing users to get you started:

Login     |  password
--------------------
admin     |  admin       <--  **This is our admin user (you probably would never have guessed)**
a         |  secret
docrosco  |  12103101

Other users can be found by looking at our database, or you can regestering your own.


Descrition/Details:
    This is the WEB adaptation of our CLI from phase 2. Navigation is a bit easier here than before. (Yay websites)

    ** Navigation **

    ** When navigating menus, the choices for you to select should be displayed as hyperlinks at the bottom
    of the page's text.

    ** Most menus should have instructions for what to do after recieving results from the database. However
     we very well may have missed printing those on some menus (a product of not having to have this application
     be polished and streamlined).It is safe to assume that for any formated list return that you see, the number
     to the left of that line will be a link to select/view that entry. When selected it should take you to
     another menu listing the tasks you can then perform for that selection.

        TLDR: If you see a list, click one of the numbers to the left of an entry.

    ** Most Pages should hav links to the main menu page, or to the search page if you're in that sub menu.
    If not, you can navigate with the back and forward buttons on the browser as needed.

     **ECT**

     ** Limited Data **
     Currently our database has some of the data we used for testing purposes still residing in it We however
     did not add in boiler plate data for your testing purposes. We assumed that you would want to do this
     yourself to better test our implementation. Often times after testing data, it was removed from the
     database to ensure integrity constraints ect. However, the application is complete, so you should be able
     to enter any and all information you need to test.

        - In extent to the above, some of the periods that are entered in the database may have reservations
        filling up the entire avail period already, so you won't be able to make reservations for those.
        We reccommend adding your own avail periods to those properties if you want to test making eservations.


PS:
We appreciate you taking the time to grade our project and hope the experience is mostly enjoyable! Let us
know if any problems arise and we'll be sure to help hash them out.

- Tanner and Gradey