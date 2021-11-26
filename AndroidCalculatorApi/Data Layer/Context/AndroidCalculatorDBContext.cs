namespace Data_Layer.Entities
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class AndroidCalculatorDBContext : DbContext
    {
        public AndroidCalculatorDBContext()
            : base("name=AndroidCalculatorDBContext")
        {
        }

        public virtual DbSet<CalculatorEquation> CalculatorEquations { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<CalculatorEquation>()
                .Property(e => e.equation)
                .IsUnicode(false);

            modelBuilder.Entity<CalculatorEquation>()
                .Property(e => e.x1)
                .IsUnicode(false);

            modelBuilder.Entity<CalculatorEquation>()
                .Property(e => e.x2)
                .IsUnicode(false);
        }
    }
}
