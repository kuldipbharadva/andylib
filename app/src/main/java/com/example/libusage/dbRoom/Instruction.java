package com.example.libusage.dbRoom;

public class Instruction {

   /*
    *   Room is a new way to create a database in your android apps, it is much similar OrmLite.
        The core framework provides built-in support for working with raw SQL content.
    *   Room provides an abstraction layer over SQLite to allow fluent database access
        while harnessing the full power of SQLite.
    *
    *   There are 3 major components in Room: Database, Entity/Model, DAO
    *
    *   Database : abstract class where we give numbers of tables of database and db version
    *              i.e, @Database(entities = {NoteModel.class}, version = 1)
    *              also we create database instance which we can use in app before use Dao method.
    *
    *   Entity   : model class with with annotation and all field are column of our table,
    *              every table have one primary key field column.
    *              don't forget to give proper annotation.
    *
    *   DAO      : Interface with @Dao annotation where we write our @Query and crud functions as per requirement.
    *
    *   We need to create Dao interface as per our db's table.
        For example, i have two table in database then
        i need to create both the table's Dao interface like NoteDao and add method as per need/requirement.

    *   Also need to create Entity class for both table's as per their required column.
    *
    *
    *   Here in demo project,
    *   I have Database(i.e, MyRoomDb.db) with only one table which name is NoteTable.
    *   So i have one Entity/Model class with their column.
            i.e, NoteModel -> Column(note_id(primary key), note_content, title)
    *
    *   One Dao interface where i declare my crud operation methods
    *
    *   One Database abstract class - NoteDatabase, where i mention our all Dao(i.e, - public abstract NoteDao getNoteDao();)
        so we can use our Dao methods.
    *
    *   Github Reference example : https://github.com/Pavneet-Sing/RoomDemo
    *   Reference : https://developer.android.com/training/data-storage/room
    */
}
