package com.mounacheikhna.extendedespresso;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.common.truth.Truth;

import java.util.ArrayList;

import static android.view.View.FIND_VIEWS_WITH_TEXT;

/**
 * Created by m.cheikhna on 11/02/2016.
 */
public final class RecyclerviewAssertions {

    public static ViewAssertion hasItemsCount(final int count) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (!(view instanceof RecyclerView)) {
                    throw noViewFoundException;
                }
                RecyclerView rv = (RecyclerView) view;
                Truth.assertThat(rv.getAdapter().getItemCount()).isEqualTo(count);
            }
        };
    }

    public static ViewAssertion hasViewWithTextAtPosition(final int index, final CharSequence text) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (!(view instanceof RecyclerView)) {
                    throw noViewFoundException;
                }
                RecyclerView rv = (RecyclerView) view;
                ArrayList<View> outviews = new ArrayList<>();
                rv.findViewHolderForAdapterPosition(index).itemView.findViewsWithText(outviews, text,
                        FIND_VIEWS_WITH_TEXT);
                Truth.assert_()
                        .withFailureMessage(
                                "There's no view at index " + index + " of recyclerview that has text : " + text)
                        .that(outviews)
                        .isNotEmpty();
            }
        };
    }

    public static ViewAssertion doesntHaveViewWithText(final String text) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (!(view instanceof RecyclerView)) {
                    throw noViewFoundException;
                }
                RecyclerView rv = (RecyclerView) view;
                ArrayList<View> outviews = new ArrayList<>();
                for (int index = 0; index < rv.getAdapter().getItemCount(); index++) {
                    rv.findViewHolderForAdapterPosition(index).itemView.findViewsWithText(outviews, text,
                            FIND_VIEWS_WITH_TEXT);
                    if (outviews.size() > 0) break;
                }
                Truth.assertThat(outviews).isEmpty();
            }
        }

    }
}
