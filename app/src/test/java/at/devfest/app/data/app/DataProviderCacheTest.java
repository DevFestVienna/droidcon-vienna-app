package at.devfest.app.data.app;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.threeten.bp.LocalDateTime;

import java.util.Arrays;
import java.util.List;

import at.devfest.app.BuildConfig;
import at.devfest.app.data.app.model.Session;
import at.devfest.app.data.app.model.Speaker;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class DataProviderCacheTest {

    private final DataProviderCache cache = new DataProviderCache();

    private final Session session1 = new Session(1, null, null, null, null, null, null, null);
    private final Session session2 = new Session(2, null, null, null, null, null, null, null);
    private final List<Session> sessions = Arrays.asList(session1, session2);

    private final Speaker speaker1 = new Speaker(1, null, null, null, null, null, null, null, null, null, null);
    private final Speaker speaker2 = new Speaker(1, null, null, null, null, null, null, null, null, null, null);
    private final List<Speaker> speakers = Arrays.asList(speaker1, speaker2);

    @Test
    public void should_save_sessions_in_memory_keeping_save_time() {
        // Given When
        LocalDateTime before = LocalDateTime.now();
        cache.setSessions(sessions);
        LocalDateTime after = LocalDateTime.now();

        // Then
        assertThat(cache.getSessions()).hasSize(2);
        assertThat(cache.getSessionsFetchedTime()).isAtLeast(before);
        assertThat(cache.getSessionsFetchedTime()).isAtMost(after);
    }

    @Test
    public void should_return_sessions_when_cache_time_is_still_active() {
        // Given
        cache.setSessionsFetchedTime(LocalDateTime.now());
        cache.setSessions(sessions);

        // When
        List<Session> result = cache.getSessions();

        // Then
        assertThat(result).isEqualTo(sessions);
    }

    @Test
    public void should_return_null_sessions_when_cache_has_expired() {
        // Given
        cache.setSessionsFetchedTime(LocalDateTime.now().minusYears(1));
        cache.setSessions(sessions);

        // When
        List<Session> result = cache.getSessions();

        // Then
        assertThat(result).isNull();
    }

    @Test
    public void should_save_speakers_in_memory_keeping_save_time() {
        // Given When
        LocalDateTime before = LocalDateTime.now();
        cache.setSpeakers(speakers);
        LocalDateTime after = LocalDateTime.now();

        // Then
        assertThat(cache.getSpeakers()).hasSize(2);
        assertThat(cache.getSpeakersFetchedTime()).isAtLeast(before);
        assertThat(cache.getSpeakersFetchedTime()).isAtMost(after);
    }

    @Test
    public void should_return_speakers_when_cache_time_is_still_active() {
        // Given
        cache.setSpeakersFetchedTime(LocalDateTime.now());
        cache.setSpeakers(speakers);

        // When
        List<Speaker> result = cache.getSpeakers();

        // Then
        assertThat(result).isEqualTo(speakers);
    }

    @Test
    public void should_return_null_speakers_when_cache_has_expired() {
        // Given
        cache.setSpeakersFetchedTime(LocalDateTime.now().minusYears(1));
        cache.setSpeakers(speakers);

        // When
        List<Speaker> result = cache.getSpeakers();

        // Then
        assertThat(result).isNull();
    }
}
