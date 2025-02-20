// Generated by view binder compiler. Do not edit!
package com.example.unilife.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.unilife.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentCalendarioBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button attivitaBtn;

  @NonNull
  public final CalendarView calendarView;

  @NonNull
  public final TextView textUnilife;

  @NonNull
  public final TextView textView23;

  private FragmentCalendarioBinding(@NonNull ConstraintLayout rootView, @NonNull Button attivitaBtn,
      @NonNull CalendarView calendarView, @NonNull TextView textUnilife,
      @NonNull TextView textView23) {
    this.rootView = rootView;
    this.attivitaBtn = attivitaBtn;
    this.calendarView = calendarView;
    this.textUnilife = textUnilife;
    this.textView23 = textView23;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentCalendarioBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentCalendarioBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_calendario, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentCalendarioBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.attivitaBtn;
      Button attivitaBtn = ViewBindings.findChildViewById(rootView, id);
      if (attivitaBtn == null) {
        break missingId;
      }

      id = R.id.calendarView;
      CalendarView calendarView = ViewBindings.findChildViewById(rootView, id);
      if (calendarView == null) {
        break missingId;
      }

      id = R.id.textUnilife;
      TextView textUnilife = ViewBindings.findChildViewById(rootView, id);
      if (textUnilife == null) {
        break missingId;
      }

      id = R.id.textView23;
      TextView textView23 = ViewBindings.findChildViewById(rootView, id);
      if (textView23 == null) {
        break missingId;
      }

      return new FragmentCalendarioBinding((ConstraintLayout) rootView, attivitaBtn, calendarView,
          textUnilife, textView23);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
