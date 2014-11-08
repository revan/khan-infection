import java.util.Collection;

/**
 * Represents a distinct group of interconnected users.
 * @author revan
 */
public class Group implements Comparable<Group> {
	private Collection<User> members;
	
	/**
	 * @param user any user in the group
	 */
	public Group(User user) {
		this.members = UserMap.getRelations(user);
	}
	
	/**
	 * @return size of the group
	 */
	public int getSize() {
		return members.size();
	}
	
	/**
	 * @return users in the group
	 */
	public Collection<User> getMembers() {
		return members;
	}
	
	/**
	 * @return one user in group
	 */
	public User getOneMember() {
		return (User) members.toArray()[0];
	}

	@Override
	public int compareTo(Group g) {
		if (members.size() < g.getSize()) {
			return -1;
		} else if (members.size() > g.getSize()) {
			return 1;
		}
		return 0;
	}
}
