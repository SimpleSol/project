package biz.growapp.base;

public interface OnBackPressedListener {
    /**
     * Take care of popping the fragment back stack or finishing the activity as appropriate.
     * @return true if backPress consumed there, false - otherwise
     */
    boolean onBackPressed();
}
