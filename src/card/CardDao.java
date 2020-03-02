package card;

import java.util.List;

public interface CardDao {
	
	int add(Card card);

	List<Card> getAll();
}
