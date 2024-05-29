package com.example.unilife.databinding;
import com.example.unilife.R;
import com.example.unilife.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityAccessoBindingImpl extends ActivityAccessoBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar2, 1);
        sViewsWithIds.put(R.id.textView5, 2);
        sViewsWithIds.put(R.id.accediButton, 3);
        sViewsWithIds.put(R.id.creaButton, 4);
        sViewsWithIds.put(R.id.textView6, 5);
        sViewsWithIds.put(R.id.textView7, 6);
        sViewsWithIds.put(R.id.editTextEmailLogin, 7);
        sViewsWithIds.put(R.id.editTextPasswordLogin, 8);
        sViewsWithIds.put(R.id.imageView, 9);
        sViewsWithIds.put(R.id.imageView2, 10);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAccessoBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private ActivityAccessoBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[3]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.Button) bindings[4]
            , (android.widget.EditText) bindings[7]
            , (android.widget.EditText) bindings[8]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            );
        this.accediLayout.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}