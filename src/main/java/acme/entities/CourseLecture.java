
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CourseLecture extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@ManyToOne
	protected Course			course;

	@ManyToOne
	protected Lecture			lecture;

}
