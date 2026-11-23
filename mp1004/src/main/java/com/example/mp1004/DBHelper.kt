package com.example.mp1004

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "account.db", null, 1) {

    // 가계부 테이블 생성
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE account (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "type TEXT, usage TEXT, date TEXT, amount INTEGER, memo TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS account")
        onCreate(db)
    }

    // 가계부 한 건 추가
    fun insert(type: String, usage: String, date: String, amount: Int, memo: String) {
        val values = ContentValues().apply {
            put("type", type)
            put("usage", usage)
            put("date", date)
            put("amount", amount)
            put("memo", memo)
        }
        writableDatabase.use { it.insert("account", null, values) }
    }

    // from~to 기간의 가계부 목록 조회 (기간이 없으면 전체)
    fun search(from: String? = null, to: String? = null): ArrayList<Account> {
        val list = ArrayList<Account>()
        readableDatabase.use { db ->
            val cursor = if (from != null && to != null) {
                db.rawQuery(
                    "SELECT * FROM account WHERE date BETWEEN ? AND ? ORDER BY id",
                    arrayOf(from, to)
                )
            } else {
                db.rawQuery("SELECT * FROM account ORDER BY id", null)
            }
            cursor.use {
                while (it.moveToNext()) {
                    list.add(
                        Account(
                            it.getInt(0), it.getString(1), it.getString(2),
                            it.getString(3), it.getInt(4), it.getString(5)
                        )
                    )
                }
            }
        }
        return list
    }
}
