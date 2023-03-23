package com.example.assignment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assignment.SQLite.QuanLySinhVienSQLite;
import com.example.assignment.model.Lop;
import com.example.assignment.model.SinhVien;

import java.util.ArrayList;

public class LopDAO {
    private QuanLySinhVienSQLite quanLySinhVienSQLite;

    public LopDAO(Context context)
    {
        quanLySinhVienSQLite = new QuanLySinhVienSQLite(context);
    }

    public ArrayList<Lop> getAll()
    {
       ArrayList<Lop> dslop = new ArrayList<>();
       SQLiteDatabase database = quanLySinhVienSQLite.getReadableDatabase();
       Cursor cursor = database.rawQuery("Select * from lop",null);
       if(cursor.getCount() != 0)
       {
           cursor.moveToFirst();
           do {
               dslop.add(new Lop(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
           }while (cursor.moveToNext());
       }
       return dslop;
    }
    public Lop getLop(String timlop) {
        SQLiteDatabase database = quanLySinhVienSQLite.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from lop where malop = ?", new String[]{timlop});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            Lop lop = new Lop(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            return lop;
        } else{return null;}

    }
    public boolean Insert(Lop lop)
    {
        try {
            SQLiteDatabase database = quanLySinhVienSQLite.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if(lop.getMalop().equalsIgnoreCase("")||lop.getTenlop().equalsIgnoreCase(""))
            {
                throw new Exception();
            }
            else
            {
                contentValues.put("malop",lop.getMalop());
                contentValues.put("tenlop",lop.getTenlop());
                contentValues.put("avatar",lop.getAvatar_lop());
            }
            long id =  database.insert("Lop",null,contentValues);
            if(id == -1)
            {
                throw new Exception();
            }
            else
            {
                return true;
            }

        }catch (Exception e)
        {
            return false;
        }
    }
    public boolean Update(Lop lop)
    {
        try{
            SQLiteDatabase database = quanLySinhVienSQLite.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if(lop.getMalop().equalsIgnoreCase("")||lop.getTenlop().equalsIgnoreCase(""))
            {
                throw new Exception();
            }
            else
            {
                contentValues.put("malop",lop.getMalop());
                contentValues.put("tenlop",lop.getTenlop());
                contentValues.put("avatar",lop.getAvatar_lop());
            }
            long id =  database.update("Lop",contentValues,"malop = ?",new String[]{lop.getMalop()});
            if(id == -1)
            {
                throw new Exception();
            }
            else
            {
                return true;
            }
        }catch (Exception e)
        {
            return false;
        }

    }
    public boolean Datele(String malop, Context context)
    {
        try {
            SQLiteDatabase database = quanLySinhVienSQLite.getWritableDatabase();
            SinhVienDAO sinhVienDAO = new SinhVienDAO(context);
            sinhVienDAO.DateleSV_1lop(malop);
            long id = database.delete("lop","malop = ? ",new String[]{malop});
            if(id == -1)
            {
                throw new Exception();
            }else {return true;}
        }catch (Exception e)
        {
            return false;
        }
    }
}
