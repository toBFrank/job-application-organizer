package com.example.jobmanager.entity;

public enum ApplicationStatus {
    APPLIED("Applied"),
    INTERVIEWING("Interviewing"), 
    OFFER("Offer"),
    REJECTED("Rejected");

    private final String displayName;

    ApplicationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static boolean isValidStatus(String status) {
        if (status == null) return false;
        try {
            ApplicationStatus.valueOf(status.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static String getAllowedStatuses() {
        StringBuilder sb = new StringBuilder();
        ApplicationStatus[] values = ApplicationStatus.values();
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i].name());
            if (i < values.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
