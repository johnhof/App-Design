/*
 * OptionScene.h
 *
 *  Created on: Mar 1, 2013
 *      Author: john
 */

#ifndef OPTIONSCENE_H_
#define OPTIONSCENE_H_

#include "cocos2d.h"

class OptionScene : public cocos2d::CCLayer
{
public:
	OptionScene();
	virtual ~OptionScene();

    // Here's a difference. Method 'init' in cocos2d-x returns bool, instead of returning 'id' in cocos2d-iphone
    virtual bool init();

    //scene pointer
    static cocos2d::CCScene* scene();

    // implement the "static node()" method manually
    CREATE_FUNC(OptionScene);

    // a selector callback
    void menuCloseCallback(CCObject* pSender);

    //menu callbacks
    void menuSoundCallback(CCObject* pSender);
    void menuReturnCallback(CCObject* pSender);

protected:
    cocos2d::CCMenu* soundMenu;
    cocos2d::CCMenuItemSprite *soundItem;
    bool soundOn;
};

#endif /* OPTIONSCENE_H_ */
