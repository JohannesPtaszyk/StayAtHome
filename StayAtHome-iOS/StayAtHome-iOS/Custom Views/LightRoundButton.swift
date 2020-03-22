
import UIKit

@IBDesignable
class LightRoundButton: UIButton {
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setup()
    }

    @IBInspectable
    var equalEdgeInset: CGFloat = 0

    @IBInspectable
    var cornerRadius: CGFloat = 16
    
    func setup() {
        
        titleEdgeInsets = UIEdgeInsets(top: equalEdgeInset, left: equalEdgeInset, bottom: equalEdgeInset, right: equalEdgeInset)
        
        layer.cornerRadius = cornerRadius
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setup()
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        setup()
    }
}
