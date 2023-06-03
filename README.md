# Universal OTP Tracker
## About the Application:-
The Universal OTP Tracker Application is a powerful tool designed to help users keep track of all their OTPs in one place. With this app, users can easily monitor and manage all their OTPs from the SMS box. The app automatically detects OTP-specific messages and sends the OTPs to a secure database for safekeeping. Users can then view all their OTPs in one place, making it easy to find and use them when needed. The app is user-friendly and easy to navigate, with a simple interface that allows users to quickly access their OTPs. Whether you're a busy professional or just someone who wants to stay organized, the universal OTP Tracker Application is the perfect solution for managing your OTPs.

## Code for Reading Messages from Message Box
### Necessary Permissions Required :-

~~~
<uses-permission android:name="android.permission.READ_SMS" />
~~~

</br>

### Code in MainActivity :-
</br>

~~~
        ActivityCompat.requestPermissions(this,
            arrayOf("android.permission.READ_SMS"), PackageManager.PERMISSION_GRANTED)

        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            viewBinding.showSms.text=""
            val uri = Uri.parse("content://sms")
            val cursor = contentResolver.query(uri, null, null, null, null)

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val body = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                    val address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                    viewBinding.showSms.append(address)

                } while (cursor.moveToNext())
            }
        } else {
            Toast.makeText(this@MainActivity,"Please Allow Notifications Manually",Toast.LENGTH_SHORT).show();
        }

~~~
