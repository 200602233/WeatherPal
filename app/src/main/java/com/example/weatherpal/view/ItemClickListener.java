package com.example.weatherpal.view;

/* Interface purpose:
- Defines the method signature for on-click handling.
- That logic/actual method functionality is then implemented in MainActivity.
- Each class that implements this interface needs to have an onclick method.
- Handling the clicks in this interface lets us decouples the click logic from the adapter class
- Helps with division of code duties I think
*/

import android.view.View;

// based on code from recyclerview lecture
public interface ItemClickListener {
    // when user clicks, we pass in the view and the item position
    // this informs which city has information displayed
    void onClick(View v, int pos);
    void unsaveCity(View v, int pos);
}
