namespace Data_Layer.Entities
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class CalculatorEquation
    {
        public int ID { get; set; }

        [Required]
        [StringLength(100)]
        public string equation { get; set; }

        [Required]
        [StringLength(70)]
        public string x1 { get; set; }

        [StringLength(70)]
        public string x2 { get; set; }

        [Required]
        public int appID { get; set; }
    }
}
