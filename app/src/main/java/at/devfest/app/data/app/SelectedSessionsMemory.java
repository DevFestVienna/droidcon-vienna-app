package at.devfest.app.data.app;

import org.threeten.bp.LocalDateTime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.devfest.app.data.app.model.Session;

@Singleton
public class SelectedSessionsMemory {

    private final Map<LocalDateTime, Integer> selectedSessions = new ConcurrentHashMap<>();

    @Inject
    public SelectedSessionsMemory() {
    }

    public boolean isSelected(Session session) {
        Integer sessionId = selectedSessions.get(session.getFromTime());
        return sessionId != null && session.getId() == sessionId;
    }

    public void setSelectedSessions(Map<LocalDateTime, Integer> selectedSessions) {
        this.selectedSessions.clear();
        this.selectedSessions.putAll(selectedSessions);
    }

    public Integer get(LocalDateTime slotTime) {
        return selectedSessions.get(slotTime);
    }

    public void toggleSessionState(at.devfest.app.data.app.model.Session session, boolean insert) {
        selectedSessions.remove(session.getFromTime());
        if (insert) {
            selectedSessions.put(session.getFromTime(), session.getId());
        }
    }
}
