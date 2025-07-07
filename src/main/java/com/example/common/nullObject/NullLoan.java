package com.example.common.nullObject;

import com.example.common.interfaces.IBook;
import com.example.common.interfaces.ILoan;
import com.example.common.interfaces.IUser;
import com.example.common.state.LoanState;

import java.time.LocalDate;

public class NullLoan implements ILoan {

    @Override public int getId(){return 0;};
    @Override public IBook getBook(){return null;};
    @Override public IUser getUser(){return null;};
    @Override public LocalDate getLoanDate(){return null;};
    @Override public LocalDate getExpirationDate(){return null;};
    @Override public LocalDate getReturnDate(){return null;};

    @Override public void setBook(IBook book){};
    @Override public void setUser(IUser user){};
    @Override public void setLoanDate(LocalDate loanDate){};
    @Override public void setExpirationDate(LocalDate expirationDate){};
    @Override public void setReturnDate(LocalDate returnDate){};


    @Override public LoanState getState(){return null;};

    @Override public boolean isReturned(){return false;};
    @Override public boolean isExpired(){return false;};
    @Override public boolean isInProgress(){return false;};


    @Override public boolean isNull(){return true;};


    @Override
    public String toString() { return ""; }
}