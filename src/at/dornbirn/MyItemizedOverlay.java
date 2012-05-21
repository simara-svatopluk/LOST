package at.dornbirn;

import java.util.ArrayList;

import org.mapsforge.android.maps.overlay.ItemizedOverlay;
import org.mapsforge.android.maps.overlay.OverlayItem;

import android.graphics.drawable.Drawable;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem>{

	ArrayList<OverlayItem> myOverlays = new ArrayList<OverlayItem>();
	
	public MyItemizedOverlay(Drawable arg0) {
		super(boundCenterBottom(arg0));
	}

	@Override
	protected OverlayItem createItem(int arg0) {
		return myOverlays.get(arg0);
	}

	@Override
	public int size() {
		return myOverlays.size();
	}
	
	public void addOverlay(OverlayItem overlay) {
        myOverlays.add(overlay);
        populate();
    }

}
