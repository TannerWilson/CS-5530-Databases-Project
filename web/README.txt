1. Compile the java code in WEB-INF/classes: javac cs5530/*.java

2. Edit the web.xml in WEB-INF; replace Your Name with your first name
and last name.

3. Access your homepage via http://georgia.eng.utah.edu:8080/~5530ux
(replace x with your user id, e.g., 5530u01).

4. You can search by login using `user1' or 'user2', or search by director
name using 'Kubrick'. 

4. To work on your own database, you need to modify the credentials in Connector.java (so that it connects to your own database, rather than the class-wide database cs5530db). Note that the sample JSP code then will not work, since the "orders" table does not exist in your own database. You can always use the distributed script orders.sql (use "source orders.sql;" in mysql client) to re-produce that table in your own database, so that the sample JSP code will work with your own database. (be careful when doing this if your database already has a "orders" table from your phase 1; that table will be overwritten. To avoid that, change the orders.sql script to have a different table name, and also change the Orders.java code to query the new table instead).

5. Place any of your JSP pages directly under the public_html folder, and your java code
with the necessary package folder structure under the WEB-INF/classes folder.
You can then access your abc.jsp page from http://georgia.eng.utah.edu:8080/~5530ux/abc.jsp. And inside your abc.jsp page, you can import your java class (see orders.jsp for an example).

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
    This is your typical command line interface (CLI) program. Due to this, navigating it can be a little
    cumbersome andannoying at times, but we've tried to make that mostly painless. That being said, there
    are a few assumptions anddesign choices that you should be aware of when using our program.

    ** Navigation **

    ** When navigating menus, the choices for you to select are printed on new lines with a number of their
     selectionprinted next to them. Enter the respective number to navigate to that menu

    ** Most menus should have instructions for what to do after recieving results from the database. However
     we very well may have missed printing those on some menus (a product of not having to have this application
     be polished and streamlined).It is safe to assume that for any formated list return that you see, the number
     to the left of that line will be theselection input for that entry. When selected it should take you to
     another menu listing the tasks you can then perform.

        TLDR: If you see a list, and the program is paused for input, enter one of the numbers to the left of
        an entry.

    ** No Going Back **
      We currenty do not support a "go back" option in every one of our menus. I wanted to put empasis on
      this as itis important toknow when navigating our application. By nature, this can be somewhat tedious
      to implement for CLI's. However, somemeuns do allow going back and others will simply pop you back up
      multiple "layers" of menus when you are finishedusing them. With that being said, if you ever reach a
      point in our application where you can't do anything, exit andrerun the program to login again and
      continue.(Apologies for this, but it is a biproduct of not needing a polished pruduct). Below is a tip
      or two on where going back is supported.

      - The property search feature and most features in it's sub menu will allow you to go back up to the
      original search screen to enter another search. This is nice because the bulk of actions dealing with
      TH's exist here.

        ***TIP*** If you find yourself having made a search you didn't want, select a house in the results,
        enter 1 (make reservation) and the press 0 (cancel) to go back to the search menu.

     **ECT**

     ** Limited Data **
     Currently our database has some of the data we used for testing purposes still residing in it We however
     did not add in boiler plate data for your testing purposes. We assumed that you would want to do this
     yourself to better test our implementation. Often times after testing data, it was removed from the
     database to ensure integrity constraints ect. However, the application is complete, so you should be able
     to enter any and all information you need to test.


PS:
We appreciate you taking the time to grade our project and hope the experience is mostly enjoyable! Let us
know if any problems arise and we'll be sure to help hash them out.

- Tanner and Gradey