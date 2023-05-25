
package acme.datatypes;

import java.text.DecimalFormat;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistic {

	int		count;

	Double	average;

	Double	max;

	Double	min;

	Double	linDev;


	public void calcAverage(final Collection<Double> values) {
		Double res;
		res = 0.0;
		if (!values.isEmpty()) {
			final Double total = values.stream().mapToDouble(Double::doubleValue).sum();
			res = total / values.size();
		}
		this.average = res;
	}

	public void calcMax(final Collection<Double> values) {
		final Double max = values.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
		this.max = max;
	}

	public void calcMin(final Collection<Double> values) {
		final Double min = values.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
		this.min = min;
	}
	public void calcLinDev(final Collection<Double> values) {
		Double res;
		Double aux;
		res = 0.0;
		if (!values.isEmpty()) {
			aux = 0.0;
			for (final Double value : values)
				aux += Math.pow(value - this.average, 2);
			res = Math.sqrt(aux / values.size());
		}
		this.linDev = res;

	}

	@Override
	public String toString() {
		final DecimalFormat df = new DecimalFormat("#.00");
		return "Statistic [count=" + df.format(this.count) + ", average=" + df.format(this.average) + ", max=" + df.format(this.max) + ", min=" + df.format(this.min) + ", stdDev=" + df.format(this.linDev) + "]";
	}

}
