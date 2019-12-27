package xyz.zhangyi.ddd.eas.projectcontext.domain;

import com.google.common.base.Strings;
import xyz.zhangyi.ddd.eas.projectcontext.domain.exceptions.AssignmentIssueException;

public class Issue {
    private IssueId issueId;
    private String name;
    private String description;
    private String ownerId;
    private IssueStatus status;

    private Issue(IssueId issueId, String name, String description) {
        this.issueId = issueId;
        this.name = name;
        this.description = description;
        this.status = IssueStatus.Open;
    }

    public static Issue of(IssueId issueId, String name, String description) {
        return new Issue(issueId, name, description);
    }

    public void assignTo(String ownerId) {
        if (Strings.isNullOrEmpty(ownerId)) {
            throw new AssignmentIssueException("owner id is null or empty.");
        }
        if (status.isResolved()) {
            throw new AssignmentIssueException("resolved issue can not be assigned.");
        }
        if (status.isClosed()) {
            throw new AssignmentIssueException("closed issue can not be assigned.");
        }
        if (this.ownerId != null && this.ownerId.equals(ownerId)) {
            throw new AssignmentIssueException("issue can not be assign to same owner again.");
        }
        this.ownerId = ownerId;
    }

    public String ownerId() {
        return ownerId;
    }

    public IssueStatus status() {
        return status;
    }

    public void changeStatusTo(IssueStatus issueStatus) {
        this.status = issueStatus;
    }
}