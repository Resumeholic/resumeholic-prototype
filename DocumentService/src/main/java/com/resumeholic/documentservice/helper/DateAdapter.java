/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.documentservice.helper;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Caleb Lam
 */
public class DateAdapter extends XmlAdapter<String, ZonedDateTime> {

    @Override
    public String marshal(ZonedDateTime v) {
        return v.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public ZonedDateTime unmarshal(String v) throws ParseException {
        return ZonedDateTime.parse(v, DateTimeFormatter.ISO_DATE_TIME);
    }

}
