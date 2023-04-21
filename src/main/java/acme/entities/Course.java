
package acme.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.datatypes.Nature;
import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[A-Z]{1,3}\\d{3}$")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	@NotNull
	protected Money				price;

	@URL
	@Length(max = 255)
	protected String			furtherInformationLink;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Lecturer			lecturer;

	protected boolean			draftMode;


	public Nature natureOfCourse(final List<Lecture> lectures) {
		Nature res;
		res = Nature.BALANCED;
		if (!lectures.isEmpty()) {
			Map<Nature, Integer> lecturesByType;
			lecturesByType = new HashMap<>();
			for (final Lecture l : lectures) {
				final Nature nature = l.getNature();
				if (lecturesByType.containsKey(nature))
					lecturesByType.put(nature, lecturesByType.get(nature) + 1);
				else
					lecturesByType.put(nature, 1);
			}

			if (lecturesByType.containsKey(Nature.HANDS_ON) && lecturesByType.containsKey(Nature.THEORETICAL)) {
				if (lecturesByType.get(Nature.HANDS_ON) > lecturesByType.get(Nature.THEORETICAL))
					res = Nature.HANDS_ON;
				else if (lecturesByType.get(Nature.HANDS_ON) < lecturesByType.get(Nature.THEORETICAL))
					res = Nature.THEORETICAL;
			} else if (lecturesByType.containsKey(Nature.HANDS_ON))
				res = Nature.HANDS_ON;
			else if (lecturesByType.containsKey(Nature.THEORETICAL))
				res = Nature.THEORETICAL;
		}
		return res;
	}
}
