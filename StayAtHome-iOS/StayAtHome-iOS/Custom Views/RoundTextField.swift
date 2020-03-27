
import UIKit

@IBDesignable
class RoundTextField: UIView {

    var textField: UITextField!
    
    private var leadingTextFieldConstraint: NSLayoutConstraint!
    private var trailingTextFieldConstraint: NSLayoutConstraint!
    private var topTextFieldConstraint: NSLayoutConstraint!
    private var bottomTextFieldConstraint: NSLayoutConstraint!
    
    @IBInspectable
    var borderWidth: CGFloat = 1 {
        didSet {
            layoutSubviews()
        }
    }
    
    @IBInspectable
    var borderColor: UIColor = .black {
        didSet {
            layoutSubviews()
        }
    }
    
    @IBInspectable
    var cornerRadius: CGFloat = 12 {
        didSet {
            layoutSubviews()
        }
    }
    
    @IBInspectable
    var equalInset: CGFloat = 0 {
        didSet {
            insets = UIEdgeInsets(top: equalInset, left: equalInset, bottom: equalInset, right: equalInset)
            layoutSubviews()
        }
    }
    
    @IBInspectable
    var placeholder: String = "" {
        didSet {
            layoutSubviews()
        }
    }
    
    @IBInspectable
    var placeholderColor: UIColor = .black {
        didSet {
            layoutSubviews()
        }
    }
    
    @IBInspectable
    var textColor: UIColor = .black {
        didSet {
            layoutSubviews()
        }
    }
    
    var text: String? {
        return textField.text
    }
    
    var insets: UIEdgeInsets! {
        didSet {
            layoutSubviews()
        }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setup()
    }
    
    
    func setup() {
        textField = UITextField()
        addSubview(textField)
        textField.translatesAutoresizingMaskIntoConstraints = false
        
        leadingTextFieldConstraint = textField.leadingAnchor.constraint(equalTo: leadingAnchor)
        trailingTextFieldConstraint = trailingAnchor.constraint(equalTo: textField.trailingAnchor)
        topTextFieldConstraint = textField.topAnchor.constraint(equalTo: topAnchor)
        bottomTextFieldConstraint = bottomAnchor.constraint(equalTo: textField.bottomAnchor)
        
        NSLayoutConstraint.activate([
            leadingTextFieldConstraint,
            trailingTextFieldConstraint,
            topTextFieldConstraint,
            bottomTextFieldConstraint
        ])
        
         insets = UIEdgeInsets()
    }
    
    private func refreshInsets() {
        leadingTextFieldConstraint.constant = insets.left
        topTextFieldConstraint.constant = insets.top
        trailingTextFieldConstraint.constant = insets.right
        bottomTextFieldConstraint.constant = insets.bottom
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        
        refreshInsets()
        
        textField.attributedPlaceholder = NSAttributedString(string: placeholder,
                           attributes: [NSAttributedString.Key.foregroundColor: placeholderColor])
        
        layer.cornerRadius = cornerRadius
        layer.borderColor = borderColor.cgColor
        layer.borderWidth = borderWidth
        textField.backgroundColor = backgroundColor
        textField.textColor = textColor
        
    }
    
}
