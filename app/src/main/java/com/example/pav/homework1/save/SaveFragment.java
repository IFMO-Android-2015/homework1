package com.example.pav.homework1.save;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Создаем фрагмет который не унижтожается при перевороте экрана.
 */
public class SaveFragment extends Fragment {
    private Container container;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setRetainInstance(true); // защита от уничтожения
    }

    public Container getModel() {
        return container;
    }

    public void setModel(Container container) {
        this.container = container;
    }
}
