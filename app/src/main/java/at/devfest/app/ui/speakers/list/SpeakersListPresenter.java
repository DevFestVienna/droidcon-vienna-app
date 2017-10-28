package at.devfest.app.ui.speakers.list;

import java.util.ArrayList;
import java.util.Collections;

import at.devfest.app.data.app.DataProvider;
import at.devfest.app.data.app.model.Speaker;
import at.devfest.app.ui.BaseFragmentPresenter;
import icepick.State;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SpeakersListPresenter extends BaseFragmentPresenter<SpeakersListMvp.View> implements SpeakersListMvp.Presenter {

    private final DataProvider dataProvider;
    @State
    ArrayList<Speaker> speakers;
    private Subscription speakersSubscription;

    public SpeakersListPresenter(SpeakersListMvp.View view, DataProvider dataProvider) {
        super(view);
        this.dataProvider = dataProvider;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (speakers == null) {
            loadData();
        } else {
            this.view.displaySpeakers(speakers);
        }
    }

    @Override
    public void onStop() {
        if (speakersSubscription != null) {
            speakersSubscription.unsubscribe();
        }
        super.onStop();
    }

    @Override
    public void reloadData() {
        loadData();
    }

    private void loadData() {
        speakersSubscription = dataProvider.getSpeakers()
                .doOnNext(speakers -> {
                    if (speakers != null) {
                        Collections.sort(speakers, (lhs, rhs) -> lhs.getName().compareTo(rhs.getName()));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(speakers -> {
                    this.speakers = new ArrayList<>(speakers);
                    view.displaySpeakers(speakers);
                }, throwable -> Timber.e(throwable, "Error getting speakers"), () -> {
                    if (speakers == null) {
                        view.displayLoadingError();
                    }
                });
    }
}
