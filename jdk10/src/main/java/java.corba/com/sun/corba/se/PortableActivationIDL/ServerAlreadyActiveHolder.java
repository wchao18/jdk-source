package com.sun.corba.se.PortableActivationIDL;

/**
* com/sun/corba/se/PortableActivationIDL/ServerAlreadyActiveHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from t:/workspace/open/src/java.corba/share/classes/com/sun/corba/se/PortableActivationIDL/activation.idl
* Monday, March 26, 2018 at 7:22:23 PM Mountain Daylight Time
*/

public final class ServerAlreadyActiveHolder implements org.omg.CORBA.portable.Streamable
{
  public com.sun.corba.se.PortableActivationIDL.ServerAlreadyActive value = null;

  public ServerAlreadyActiveHolder ()
  {
  }

  public ServerAlreadyActiveHolder (com.sun.corba.se.PortableActivationIDL.ServerAlreadyActive initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.sun.corba.se.PortableActivationIDL.ServerAlreadyActiveHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.sun.corba.se.PortableActivationIDL.ServerAlreadyActiveHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.sun.corba.se.PortableActivationIDL.ServerAlreadyActiveHelper.type ();
  }

}
